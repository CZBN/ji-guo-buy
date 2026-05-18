<template>
	<view>
		<view class="order-rule">
			<text @tap="changeOrder('new')" :class="order == 'new' ? 'current' : ''">最新</text>
			<text @tap="changeOrder('hot')" :class="order == 'hot' ? 'current' : ''">最热</text>
		</view>

		<view class="guide-items-wrap">
			<scroll-view scroll-y="true" :show-scrollbar="false" @scrolltolower="getMore" class="scroll-container">
				<view class="two-col-wrap">
					<view class="guide-item-view" v-for="item in guideItems" :key="item.id" @tap="gotoDetail(item)">
						<guide-item-card @clickthumb="doThumb" :id="item.id" :title="item.title" :name="item.name"
							:thumb-count="item.thumbCount" :comment-count="item.commentCount"
							:image="buildImageUrl(item.image)"></guide-item-card>
					</view>
				</view>
				<view class="bottom-tips" v-if="isLastPage">--我是有底线的--</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import GuideItemCard from '@/components/GuideItemCard.vue';
	export default {
		data() {
			return {
				order: "new",
				pno: 1,
				guideItems: [],
				imageUrl: "",
				isLastPage: false,
				loading: false
			}
		},
		components: {
			GuideItemCard
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
			this.refresh();
		},
		methods: {
			buildImageUrl(imagePath) {
				if (!imagePath) return "";
				return this.imageUrl + "/" + imagePath;
			},
			refresh() {
				this.pno = 1;
				this.isLastPage = false;
				this.guideItems = [];
				this.getData();
			},
			getMore() {
				if (this.isLastPage || this.loading) {
					return;
				}
				this.pno++;
				this.getData();
			},
			changeOrder(newOrder) {
				if (newOrder === this.order) {
					return;
				}
				this.order = newOrder;
				this.refresh();
			},
			getData() {
				this.loading = true;
				uni.request({
					url: "/api/guideitem/search",
					data: {
						order: this.order,
						pno: this.pno,
						size: 10
					},
					success: (resp) => {
						if (resp.data.code !== 0 || !resp.data.data) {
							uni.showToast({ title: resp.data.message || "暂无数据", icon: "none" });
							this.isLastPage = true;
							return;
						}
						this.isLastPage = resp.data.data.isLastPage;
						const items = (resp.data.data.list || []).map(item => ({
							...item,
							name: item.name || "精选导购"
						}));
						this.guideItems = this.guideItems.concat(items);
					},
					fail: function() {
						uni.showToast({
							title: "出错了",
							icon: 'error'
						})
					},
					complete: () => {
						this.loading = false;
					}
				})
			},
			gotoDetail(item) {
				uni.navigateTo({
					url: "/pages/guidedetail/guidedetail?id=" + item.id
				});
			},
			doThumb() {
				uni.showToast({ title: "导购点赞功能待完善", icon: "none" });
			}
		}
	}
</script>

<style scoped>
	.order-rule {
		width: 92%;
		margin: 0 4% 15rpx;
		border-radius: 8rpx;
		background-color: #f5f5f5;
		padding: 12rpx 0;
		box-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.06);
	}

	.order-rule text {
		display: inline-block;
		width: 50%;
		text-align: center;
		font-size: 28rpx;
		color: #666;
	}

	.order-rule .current {
		color: red;
		font-weight: bold;
		border-bottom: 4rpx solid red;
	}

	.guide-items-wrap .scroll-container {
		width: 100%;
		height: 70vh;
	}

	.two-col-wrap {
		display: flex;
		flex-wrap: wrap;
		width: 92%;
		margin: 0 4%;
	}

	.guide-item-view {
		width: 50%;
		padding: 12rpx;
		box-sizing: border-box;
	}

	.bottom-tips {
		text-align: center;
		color: gray;
		font-size: 24rpx;
		margin: 20rpx 0;
	}
</style>
